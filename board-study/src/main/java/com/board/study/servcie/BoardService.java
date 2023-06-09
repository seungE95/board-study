package com.board.study.servcie;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.study.dto.board.BoardRequestDto;
import com.board.study.dto.board.BoardResponseDto;
import com.board.study.entity.board.Board;
import com.board.study.entity.board.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	@Transactional
	public Long save(BoardRequestDto boardSaveDto) {
		System.out.println("data ::: "+boardSaveDto.toEntity().getId()+", "+boardSaveDto.toEntity().getContent());
		return boardRepository.save(boardSaveDto.toEntity()).getId();
	}
	
	/*
	 * 트랜잭션에 readOnly=true 옵션을 주면 스프링 프레임워크가 하이버네이트 세션 플러시 모드 Manual로 설정한다
	 * 이렇게 하면 강제로 플러시를 호출하지 않는 한 플러시가 일어나지 않는다.
	 * 따라서 트랜잭션을 커밋하더라도 영속성 컨텍스트가 플러시 되지 않아서 엔티티의 등록, 수정, 삭제가 동작하지 않고,
	 * 또한 읽기 전용으로, 영속성 컨텍스트는 변경 감지를 위한 스냅샷을 보관하지 않으므로 성능이 향샹된다.
	 * */
	
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAll(Integer page, Integer size){
		
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		
		Page<Board> list = boardRepository.findAll(PageRequest.of(page, size));
		
		resultMap.put("list",
				list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());
		
		return resultMap;
	}
	
	public BoardResponseDto findById(Long id) {
		boardRepository.updateBoardReadCntInc(id);
		return new BoardResponseDto(boardRepository.findById(id).get());
	}
	
	public int updateBoard(BoardRequestDto boardRequestDto) {
		return boardRepository.updateBoard(boardRequestDto);
	}
	
	public void deleteById(Long id) {
		boardRepository.deleteById(id);
	}
	
	public int deleteAll(Long[] deleteId) {
		return boardRepository.deleteBoard(deleteId);
	}
	
}
