package com.parkvita.admin.springboot.service.posts;

import com.parkvita.admin.springboot.domain.posts.Posts;
import com.parkvita.admin.springboot.domain.posts.PostsRepository;
import com.parkvita.admin.springboot.web.dto.PostsListResponseDto;
import com.parkvita.admin.springboot.web.dto.PostsResponseDto;
import com.parkvita.admin.springboot.web.dto.PostsSaveRequestDto;
import com.parkvita.admin.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    // 게시글 작성
    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    // 게시글 수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        postsRepository.delete(posts);      //jpaRepository에서 이미 delete 메소드를 지원하고있음
                                            // Entity 파라미터로 삭제할 수 도 있고 deleteById 메소드를 이용하면 ID로도 삭제할 수 있음
                                            // 존재하는 Posts인지 확인을 위해 엔티티 조회 후 그대로 삭제
    }

}
