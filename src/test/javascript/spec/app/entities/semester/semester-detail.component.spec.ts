/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { ItehProjectTestModule } from '../../../test.module';
import { SemesterDetailComponent } from '../../../../../../main/webapp/app/entities/semester/semester-detail.component';
import { SemesterService } from '../../../../../../main/webapp/app/entities/semester/semester.service';
import { Semester } from '../../../../../../main/webapp/app/entities/semester/semester.model';

describe('Component Tests', () => {

    describe('Semester Management Detail Component', () => {
        let comp: SemesterDetailComponent;
        let fixture: ComponentFixture<SemesterDetailComponent>;
        let service: SemesterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [SemesterDetailComponent],
                providers: [
                    SemesterService
                ]
            })
            .overrideTemplate(SemesterDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SemesterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SemesterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Semester(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.semester).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
