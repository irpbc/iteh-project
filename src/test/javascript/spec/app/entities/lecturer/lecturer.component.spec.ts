/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { ItehProjectTestModule } from '../../../test.module';
import { LecturerComponent } from '../../../../../../main/webapp/app/entities/lecturer/lecturer.component';
import { LecturerService } from '../../../../../../main/webapp/app/entities/lecturer/lecturer.service';
import { Lecturer } from '../../../../../../main/webapp/app/entities/lecturer/lecturer.model';

describe('Component Tests', () => {

    describe('Lecturer Management Component', () => {
        let comp: LecturerComponent;
        let fixture: ComponentFixture<LecturerComponent>;
        let service: LecturerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [LecturerComponent],
                providers: [
                    LecturerService
                ]
            })
            .overrideTemplate(LecturerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LecturerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LecturerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Lecturer(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.lecturers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
