/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { ItehProjectTestModule } from '../../../test.module';
import { OptionalCourseGroupDetailComponent } from '../../../../../../main/webapp/app/entities/optional-course-group/optional-course-group-detail.component';
import { OptionalCourseGroupService } from '../../../../../../main/webapp/app/entities/optional-course-group/optional-course-group.service';
import { OptionalCourseGroup } from '../../../../../../main/webapp/app/entities/optional-course-group/optional-course-group.model';

describe('Component Tests', () => {

    describe('OptionalCourseGroup Management Detail Component', () => {
        let comp: OptionalCourseGroupDetailComponent;
        let fixture: ComponentFixture<OptionalCourseGroupDetailComponent>;
        let service: OptionalCourseGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [OptionalCourseGroupDetailComponent],
                providers: [
                    OptionalCourseGroupService
                ]
            })
            .overrideTemplate(OptionalCourseGroupDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OptionalCourseGroupDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OptionalCourseGroupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new OptionalCourseGroup(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.optionalCourseGroup).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
