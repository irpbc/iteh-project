/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { ItehProjectTestModule } from '../../../test.module';
import { LecturerDetailComponent } from '../../../../../../main/webapp/app/entities/lecturer/lecturer-detail.component';
import { LecturerService } from '../../../../../../main/webapp/app/entities/lecturer/lecturer.service';
import { Lecturer } from '../../../../../../main/webapp/app/entities/lecturer/lecturer.model';

describe('Component Tests', () => {

    describe('Lecturer Management Detail Component', () => {
        let comp: LecturerDetailComponent;
        let fixture: ComponentFixture<LecturerDetailComponent>;
        let service: LecturerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [LecturerDetailComponent],
                providers: [
                    LecturerService
                ]
            })
            .overrideTemplate(LecturerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LecturerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LecturerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Lecturer(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.lecturer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
