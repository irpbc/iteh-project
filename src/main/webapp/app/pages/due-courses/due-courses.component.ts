import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE } from '../../shared';
import { Course } from '../../entities/course';
import { Headers, Http, Response } from '@angular/http';

@Component({
    selector: 'app-due-courses',
    templateUrl: './due-courses.component.html'
})
export class DueCoursesComponent implements OnInit {

    courses: DueCourse[];
    error: any;
    success: any;
    routeData: any;
    totalItems: number;
    itemsPerPage: number;
    page: number;
    previousPage: number;

    constructor(private jhiAlertService: JhiAlertService,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private http: Http) {

        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
        });
    }

    loadAll() {
        this.http.get('/api/due-courses', {
            params: {
                page: (this.page - 1).toString(),
                size: this.itemsPerPage.toString()
            }
        }).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/due-courses'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage
            }
        });
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
    }

    trackId(index: number, item: Course) {
        return item.id;
    }

    private onSuccess(data: DueCourse[], headers: Headers) {
        this.totalItems = +headers.get('X-Total-Count');
        // this.page = pagingParams.page;
        this.courses = data;
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

interface DueCourse {
    name: string;
    espbPoints: number;
    yearOfStudies: number;
}
