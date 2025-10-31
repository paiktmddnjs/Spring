package com.kh.spring.service;

import com.kh.spring.model.mapper.BoardMapper;
import com.kh.spring.model.vo.Attachment;
import com.kh.spring.model.vo.Board;
import com.kh.spring.model.vo.Category;
import com.kh.spring.model.vo.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final SqlSession sqlSession;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper, SqlSession sqlSession){

        this.boardMapper = boardMapper;
        this.sqlSession = sqlSession;
    }

    @Override
    public ArrayList<Board> selectAllBoard(PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());

        //List<Board> list = boardMapper.selectAllBoard(rowBounds);
        List<Board> list = sqlSession.selectList("com.kh.spring.model.mapper.BoardMapper.selectAllBoard", null, rowBounds);
        return new ArrayList<>(list);
    }

    @Override
    public int increaseCount(int boardNo) {
        return boardMapper.increaseCount(boardNo);
    }

    @Override
    public int selectAllBoardCount() {
        return boardMapper.selectAllBoardCount();
    }


    @Override
    public Board selectBoardByBoardNo(int boardNo) {
        return boardMapper.selectBoardByBoardNo(boardNo);
    }

    public ArrayList<Category> selectAllCategory() {
        return boardMapper.selectAllCategory();
    }

    public int insertBoard(Board board, Attachment at) {

        int result1 = boardMapper.insertBoard(board); // 게시글 테이블에 데이터 삽입

        // 2. 첨부파일 등록 (선택)
        int result2 = 1; // 기본값은 1 (첨부파일이 없어도 성공 처리)
        if (at != null) {
            // 게시글 등록 시 생성된 BOARD_NO를 Attachment 객체에 설정해야 함
            // 방법: MyBatis의 <insert useGeneratedKeys="true" ...> 또는 <selectKey> 사용
            at.setRefBoardNo(board.getBoardNo());
            result2 = boardMapper.insertAttachment(at); // 첨부파일 테이블에 데이터 삽입
        }

        // 두 작업 모두 성공했을 때만 최종 성공으로 간주
        return result1 * result2; // 0 (실패) 또는 1 (성공) 반환

    }

    public Attachment selectAttachment(int boardNo) {

        return boardMapper.selectAttachment(boardNo);

    }

}