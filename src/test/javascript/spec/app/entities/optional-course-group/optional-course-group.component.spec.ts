/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { ItehProjectTestModule } from '../../../test.module';
import { OptionalCourseGroupComponent } from '../../../../../../main/webapp/app/entities/optional-course-group/optional-course-group.component';
import { OptionalCourseGroupService } from '../../../../../../main/webapp/app/entities/optional-course-group/optional-course-group.service';
import { OptionalCourseGroup } from '../../../../../../main/webapp/app/entities/optional-course-group/optional-course-group.model';

describe('Component Tests', () => {

    describe('OptionalCourseGroup Management Component', () => {
        let comp: OptionalCourseGroupComponent;
        let fixture: ComponentFixture<OptionalCourseGroupComponent>;
        let service: OptionalCourseGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [OptionalCourseGroupComponent],
                providers: [
                    OptionalCourseGroupService
                ]
            })
            .overrideTemplate(OptionalCourseGroupComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OptionalCourseGroupComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OptionalCourseGroupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new OptionalCourseGroup(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.optionalCourseGroups[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
