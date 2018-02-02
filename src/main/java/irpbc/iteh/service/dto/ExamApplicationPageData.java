package irpbc.iteh.service.dto;

import irpbc.iteh.domain.Exam;
import irpbc.iteh.domain.StudentExam;

import java.util.List;

/**
 * Created by ivan on 29.1.18..
 */
public class ExamApplicationPageData {

    private ExamPeriodDTO nextPeriod;
    private List<ExamDTO> exams;
    private List<ExamDTO> appliedExams;

    public ExamApplicationPageData(ExamPeriodDTO nextPeriod, List<ExamDTO> exams, List<ExamDTO> appliedExams) {
        this.nextPeriod = nextPeriod;
        this.exams = exams;
        this.appliedExams = appliedExams;
    }

    public ExamPeriodDTO getNextPeriod() {
        return nextPeriod;
    }

    public void setNextPeriod(ExamPeriodDTO nextPeriod) {
        this.nextPeriod = nextPeriod;
    }

    public List<ExamDTO> getExams() {
        return exams;
    }

    public void setExams(List<ExamDTO> exams) {
        this.exams = exams;
    }

    public List<ExamDTO> getAppliedExams() {
        return appliedExams;
    }

    public void setAppliedExams(List<ExamDTO> appliedExams) {
        this.appliedExams = appliedExams;
    }
}
