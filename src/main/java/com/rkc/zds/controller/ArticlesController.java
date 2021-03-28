package com.rkc.zds.controller;

import java.util.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkc.zds.api.exception.InvalidRequestException;
import com.rkc.zds.dto.GroupMemberElementDto;
import com.rkc.zds.entity.ArticleEntity;
import com.rkc.zds.entity.ArticleTagArticleEntity;
import com.rkc.zds.entity.ArticleTagEntity;
import com.rkc.zds.entity.ContactEntity;
import com.rkc.zds.entity.GroupMemberEntity;
import com.rkc.zds.entity.UserEntity;
import com.rkc.zds.model.ArticleData;
import com.rkc.zds.model.ArticleDataList;
import com.rkc.zds.repository.ArticleRepository;
import com.rkc.zds.repository.ArticleTagArticleRepository;
import com.rkc.zds.repository.ArticleTagRepository;
import com.rkc.zds.repository.UserRepository;
import com.rkc.zds.service.ArticleQueryService;
import com.rkc.zds.service.ArticleReadService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/api/articles")
public class ArticlesController {
	private ArticleRepository articleRepository;
	private ArticleQueryService articleQueryService;
	private ArticleReadService articleReadService;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ArticleTagRepository tagRepository;

	@Autowired
	ArticleTagArticleRepository tagArticleRepository;

	@Autowired
	public ArticlesController(ArticleRepository articleRepository, ArticleQueryService articleQueryService,
			ArticleReadService articleReadService) {
		this.articleRepository = articleRepository;
		this.articleQueryService = articleQueryService;
		this.articleReadService = articleReadService;
	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {
			"application/json;charset=UTF-8" }, produces = { "application/json;charset=UTF-8" })

	public ResponseEntity createArticle(@RequestBody String jsonString) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String userLogin = authentication.getName();

		Optional<UserEntity> userDto = userRepository.findByUserName(userLogin);

		ArticleEntity article = new ArticleEntity();

		ObjectMapper mapper = new ObjectMapper();

		UserEntity user = null;
		if (userDto.isPresent()) {
			user = userDto.get();

			try {
				article = mapper.readValue(jsonString, ArticleEntity.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			article.setUserId(user.getId());
			article.setSlug(article.toSlug(article.getTitle()));

			Timestamp stamp = new Timestamp(new Date().getTime());
			article.setCreatedAt(stamp);
			article.setUpdatedAt(stamp);

			article = articleRepository.save(article);
			
			if (article.getTags() != null) {
				processTags(article);
			}

			final Integer articleId = article.getId();
			final UserEntity temp = user;

			return ResponseEntity.ok(new HashMap<String, Object>() {
				{
					put("article", articleQueryService.findById(articleId, temp).get());
				}
			});
		}

		return ResponseEntity.ok(HttpStatus.NO_CONTENT);
	}

	private void processTags(ArticleEntity article) {

		String tags = article.getTags();
		String[] array = tags.split("\\s+");
		ArticleTagEntity tagDto = null;
		ArticleTagArticleEntity tagArticleDto = null;

		List<ArticleTagArticleEntity> articleTagList = null;

		for (String tag : array) {
			if (!tag.equals("")) {
				tagDto = tagRepository.findByName(tag);
				if (tagDto == null) {
					tagDto = new ArticleTagEntity();
					tagDto.setName(tag);
					tagDto = tagRepository.save(tagDto);
				}
				if (tagDto != null) {
					tagArticleDto = tagArticleRepository.findByTagIdAndArticleId(tagDto.getId(), article.getId());
					if (tagArticleDto == null) {
						tagArticleDto = new ArticleTagArticleEntity();
						tagArticleDto.setTagId(tagDto.getId());
						tagArticleDto.setArticleId(article.getId());
						tagArticleDto = tagArticleRepository.save(tagArticleDto);
					}
				}
			}
		}
	}
	
	@RequestMapping(value = "feed", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ArticleData>> getFeed(Pageable pageable,
			@RequestParam(value = "offset", defaultValue = "0") int offset,
			@RequestParam(value = "limit", defaultValue = "20") int limit,
			@RequestParam(value = "tag", required = false) String tag,
			@RequestParam(value = "favorited", required = false) String favoritedBy,
			@RequestParam(value = "author", required = false) String author) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String userLogin = authentication.getName();

		Optional<UserEntity> userDto = userRepository.findByUserName(userLogin);

		UserEntity user = null;

		if (userDto.isPresent()) {
			user = userDto.get();
		}

		ArticleDataList list = articleQueryService.findUserFeed(pageable, user);

		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

		PageImpl<ArticleData> page = new PageImpl<ArticleData>(list.getList(), pageRequest, list.getCount());

		return new ResponseEntity<>(page, HttpStatus.OK);

	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ArticleData>> getArticles(Pageable pageable,
			@RequestParam(value = "offset", defaultValue = "0") int offset,
			@RequestParam(value = "limit", defaultValue = "20") int limit,
			@RequestParam(value = "tag", required = false) String tag,
			@RequestParam(value = "favorited", required = false) String favoritedBy,
			@RequestParam(value = "author", required = false) String author) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String userLogin = authentication.getName();

		Optional<UserEntity> userDto = userRepository.findByUserName(userLogin);

		UserEntity user = null;

		if (userDto.isPresent()) {
			user = userDto.get();
		}

		Page<ArticleEntity> pageList = null;
		if (author != null) {
			Optional<UserEntity> authorDto = userRepository.findByUserName(author);

			UserEntity userDtoX = null;

			if (authorDto.isPresent()) {
				userDtoX = authorDto.get();
			}

			pageList = articleReadService.findByUserId(pageable, userDtoX.getId());
		} else if (favoritedBy != null) {
			Optional<UserEntity> authorDto = userRepository.findByUserName(favoritedBy);

			UserEntity userDtoX = null;

			if (authorDto.isPresent()) {
				userDtoX = authorDto.get();
			}

			pageList = articleReadService.findFavorites(pageable, userDtoX.getId());

		} else if (tag != null) {

			pageList = articleReadService.findByTag(pageable, tag);

		}

		else {
			pageList = articleReadService.findAll(pageable);

		}

		Pageable pageOptions = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

		// ArticleDataList list = articleQueryService.findRecentArticles(pageOptions, tag, author, favoritedBy, user);

		ArticleDataList list = articleQueryService.convertToArticleData(pageList);
		
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

		PageImpl<ArticleData> page = new PageImpl<ArticleData>(list.getList(), pageRequest,
				pageList.getTotalElements());

		return new ResponseEntity<>(page, HttpStatus.OK);

	}

}
