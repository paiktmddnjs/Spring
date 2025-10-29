package com.kh.spring.controller;


import com.kh.spring.model.vo.Attachment;
import com.kh.spring.model.vo.Board;
import com.kh.spring.model.vo.Category;
import com.kh.spring.model.vo.PageInfo;
import com.kh.spring.service.BoardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class BoardController {


    private final BoardService boardService;
    private static final int FILE_MAX_SIZE = 1024 * 1024 * 50; // 50MB
    private static final int REQUEST_MAX_SIZE = 1024 * 1024 * 60; // 60MB

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

    @PostMapping("/insert.bo")
    public String insertBoard(
            @ModelAttribute Board b,
            @RequestParam(value = "upfile", required = false) MultipartFile upfile,
            HttpSession session,
            RedirectAttributes ra) {

        String savePath = session.getServletContext().getRealPath("/resources/board-file/");
        File dir = new File(savePath);
        if (!dir.exists()) dir.mkdirs();

        Attachment at = null;

        if (upfile != null && !upfile.isEmpty()) {
            String originName = upfile.getOriginalFilename();
            String ext = originName.substring(originName.lastIndexOf("."));
            String changeName = "kh_" + System.currentTimeMillis() + ext;

            try {
                upfile.transferTo(new File(savePath + changeName));
                at = new Attachment();
                at.setOriginName(originName);
                at.setChangeName(changeName);
                at.setFilePath("resources/board-file/");
            } catch (IOException e) {
                e.printStackTrace();
                ra.addFlashAttribute("alertMsg", "파일 업로드 중 오류 발생");
                return "redirect:/list.bo";
            }
        }

        b.setBoardWriter(1); // 테스트용 작성자

        int result = boardService.insertBoard(b, at);

        if (result > 0) {
            ra.addFlashAttribute("alertMsg", "게시글 작성 성공!");
            return "redirect:/list.bo";
        } else {
            ra.addFlashAttribute("alertMsg", "게시글 작성 실패");
            return "redirect:/list.bo";
        }
    }

}