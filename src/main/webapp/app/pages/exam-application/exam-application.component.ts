import { Component, OnInit } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Exam } from '../../entities/exam/exam.model';
import { ExamPeriod } from '../../entities/exam-period/exam-period.model';

@Component({
    selector: 'app-exam-application',
    templateUrl: './exam-application.component.html',
    styles: []
})
export class ExamApplicationComponent implements OnInit {

    data: ExamApplicationPageData;

    constructor(private http: Http) {
    }

    ngOnInit() {
        this.loadData();
    }

    loadData() {
        this.http.get('/api/exam-application-data').subscribe(
            (res: Response) => {
                this.data = res.json();
            }
        );
    }

    trackId(index, item) {
        return item.id;
    }

    apply(exam: Exam) {
        this.http.put('/api/apply-for-exam', null, { params: { exam: exam.id } }).subscribe(
            (res: Response) => {
                this.data = res.json();
            }
        );
    }

    cancel(exam: Exam) {
        this.http.put('/api/cancel-exam-application', null, { params: { exam: exam.id } }).subscribe(
            (res: Response) => {
                this.data = res.json();
            }
        );
    }
}

interface ExamApplicationPageData {
    nextPeriod: ExamPeriod;
    exams: Exam[];
    appliedExams: Exam[];
}
