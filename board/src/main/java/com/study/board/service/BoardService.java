package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repositoy.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception{

        // 저장할 경로 지정
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        // 파일 이름에 붙힐 랜덤 이름 생성
        UUID uuid = UUID.randomUUID();

        // 파일 이름에 랜덤 이름을 붙힘
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        // 예외처리를 해줘야 하는 원인이 되는 메소드
        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){

        return boardRepository.findAll(pageable);
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id){

        return boardRepository.findById(id).get();
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id){

        boardRepository.deleteById(id);
    }

    // 특정 게시글 불러오기
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }
}
