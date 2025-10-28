package com.kh.spring.controller;


import com.kh.spring.model.vo.Board;
import com.kh.spring.model.vo.Category;
import com.kh.spring.model.vo.PageInfo;
import com.kh.spring.service.BoardService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;


@Controller
public class BoardController {


    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {

        this.boardService = boardService;

    }

        @GetMapping("list.bo")
        public String selectAllBoard(@RequestParam(value = "cpage", required = false) Integer cpage , Model model) {
            int currentPage = (cpage != null) ? cpage : 1; // 요청한 페이지 또는 기본값
            int listCount = boardService.selectAllBoardCount();

            int pageLimit = 5; // 페이지 버튼 개수
            int boardLimit = 5; // 페이지당 게시글 수

            PageInfo pi = new PageInfo(currentPage, listCount, pageLimit, boardLimit);

            List<Board> list = boardService.selectAllBoard(pi);

            model.addAttribute("list", list);
            model.addAttribute("pi", pi);

            return "/board/listView"; // 포워드할 뷰 경로
        }


        @GetMapping("detail.bo")
        public String selectDetailBoard(@RequestParam(value = "bno", required = true) Integer boardNo, Model model) {

            int boardNoInt = boardNo != null ? boardNo : 0;
            int result = boardService.increaseCount(boardNoInt);
            Board board = boardService.selectBoardByBoardNo(boardNoInt);
            model.addAttribute("board", board);
            return "/board/detailView";
        }

        @GetMapping("enrollForm.bo")
        public String enrollFormBoard( Model model) {

            ArrayList<Category> categories = boardService.selectAllCategory();

            model.addAttribute("categories", categories);

            return "/board/enrollForm";

        }



    }
