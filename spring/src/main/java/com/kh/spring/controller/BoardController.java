package com.kh.spring.controller;

import ch.qos.logback.core.model.Model;
import com.kh.spring.model.vo.Board;
import com.kh.spring.model.vo.PageInfo;
import com.kh.spring.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
public class BoardController {


    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {

        this.boardService = boardService;

    }

        @PostMapping("/list.bo")
        public String selectAllBoard(@RequestParam(value = "cpage", required = false) Integer cpage,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
            int currentPage = (cpage != null) ? cpage : 1; // 요청한 페이지 또는 기본값
            int listCount = boardService.selectAllBoardCount();

            int pageLimit = 5; // 페이지 버튼 개수
            int boardLimit = 5; // 페이지당 게시글 수

            PageInfo pi = new PageInfo(currentPage, listCount, pageLimit, boardLimit);

            ArrayList<Board> list = boardService.selectAllBoard(pi);

            request.setAttribute("list", list);
            request.setAttribute("pi", pi);

            return "/WEB-INF/views/board/listView"; // 포워드할 뷰 경로
        }
    }
