package com.zhihuitong.web.controller.student;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.zhihuitong.common.annotation.Log;
import com.zhihuitong.common.core.controller.BaseController;
import com.zhihuitong.common.core.domain.AjaxResult;
import com.zhihuitong.common.core.page.TableDataInfo;
import com.zhihuitong.common.enums.BusinessType;
import com.zhihuitong.common.utils.poi.ExcelUtil;
import com.zhihuitong.system.domain.Score;
import com.zhihuitong.system.service.IScoreService;

/**
 * 成绩管理Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/student/score")
public class ScoreController extends BaseController
{
    @Autowired
    private IScoreService scoreService;

    /**
     * 获取成绩列表
     */
    @PreAuthorize("@ss.hasPermi('student:score:list')")
    @GetMapping("/list")
    public TableDataInfo list(Score score)
    {
        startPage();
        List<Score> list = scoreService.selectScoreList(score);
        return getDataTable(list);
    }

    /**
     * 通过学号获取成绩列表
     */
    @PreAuthorize("@ss.hasPermi('student:score:query')")
    @GetMapping("/student/{studentNo}")
    public AjaxResult getScoreByStudent(@PathVariable String studentNo)
    {
        List<Score> list = scoreService.selectScoreByStudentNo(studentNo);
        return success(list);
    }

    /**
     * 导出成绩列表
     */
    @Log(title = "成绩管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('student:score:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Score score)
    {
        List<Score> list = scoreService.selectScoreList(score);
        ExcelUtil<Score> util = new ExcelUtil<Score>(Score.class);
        util.exportExcel(response, list, "成绩数据");
    }

    /**
     * 根据成绩ID获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('student:score:query')")
    @GetMapping(value = "/{scoreId}")
    public AjaxResult getInfo(@PathVariable Long scoreId)
    {
        return success(scoreService.selectScoreById(scoreId));
    }

    /**
     * 新增成绩
     */
    @PreAuthorize("@ss.hasPermi('student:score:add')")
    @Log(title = "成绩管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Score score)
    {
        return toAjax(scoreService.insertScore(score));
    }

    /**
     * 修改成绩
     */
    @PreAuthorize("@ss.hasPermi('student:score:edit')")
    @Log(title = "成绩管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Score score)
    {
        return toAjax(scoreService.updateScore(score));
    }

    /**
     * 删除成绩
     */
    @PreAuthorize("@ss.hasPermi('student:score:remove')")
    @Log(title = "成绩管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{scoreIds}")
    public AjaxResult remove(@PathVariable Long[] scoreIds)
    {
        return toAjax(scoreService.deleteScoreByIds(scoreIds));
    }

    /**
     * 导入成绩数据
     */
    @Log(title = "成绩管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('student:score:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Score> util = new ExcelUtil<Score>(Score.class);
        List<Score> scoreList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = scoreService.importScore(scoreList, updateSupport, operName);
        return success(message);
    }

    /**
     * 下载导入成绩模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Score> util = new ExcelUtil<Score>(Score.class);
        util.importTemplateExcel(response, "成绩数据");
    }
}
