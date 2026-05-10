package com.zhihuitong.system.mapper;

import java.util.List;
import com.zhihuitong.system.domain.Score;

/**
 * 成绩管理Mapper接口
 *
 * @author ruoyi
 */
public interface ScoreMapper
{
    /**
     * 查询成绩列表
     *
     * @param score 成绩信息
     * @return 成绩集合
     */
    public List<Score> selectScoreList(Score score);

    /**
     * 通过成绩ID查询成绩
     *
     * @param scoreId 成绩ID
     * @return 成绩对象信息
     */
    public Score selectScoreById(Long scoreId);

    /**
     * 通过学号查询成绩列表
     *
     * @param studentNo 学号
     * @return 成绩集合
     */
    public List<Score> selectScoreByStudentNo(String studentNo);

    /**
     * 新增成绩信息
     *
     * @param score 成绩信息
     * @return 结果
     */
    public int insertScore(Score score);

    /**
     * 修改成绩信息
     *
     * @param score 成绩信息
     * @return 结果
     */
    public int updateScore(Score score);

    /**
     * 删除成绩信息
     *
     * @param scoreId 成绩ID
     * @return 结果
     */
    public int deleteScoreById(Long scoreId);

    /**
     * 批量删除成绩信息
     *
     * @param scoreIds 需要删除的成绩ID
     * @return 结果
     */
    public int deleteScoreByIds(Long[] scoreIds);

    /**
     * 根据学号删除成绩
     *
     * @param studentNo 学号
     * @return 结果
     */
    public int deleteScoreByStudentNo(String studentNo);
}
