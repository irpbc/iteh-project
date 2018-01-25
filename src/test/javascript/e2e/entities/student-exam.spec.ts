import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('StudentExam e2e test', () => {

    let navBarPage: NavBarPage;
    let studentExamDialogPage: StudentExamDialogPage;
    let studentExamComponentsPage: StudentExamComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load StudentExams', () => {
        navBarPage.goToEntity('student-exam');
        studentExamComponentsPage = new StudentExamComponentsPage();
        expect(studentExamComponentsPage.getTitle()).toMatch(/app.studentExam.home.title/);

    });

    it('should load create StudentExam dialog', () => {
        studentExamComponentsPage.clickOnCreateButton();
        studentExamDialogPage = new StudentExamDialogPage();
        expect(studentExamDialogPage.getModalTitle()).toMatch(/app.studentExam.home.createOrEditLabel/);
        studentExamDialogPage.close();
    });

   /* it('should create and save StudentExams', () => {
        studentExamComponentsPage.clickOnCreateButton();
        studentExamDialogPage.getAttendedInput().isSelected().then((selected) => {
            if (selected) {
                studentExamDialogPage.getAttendedInput().click();
                expect(studentExamDialogPage.getAttendedInput().isSelected()).toBeFalsy();
            } else {
                studentExamDialogPage.getAttendedInput().click();
                expect(studentExamDialogPage.getAttendedInput().isSelected()).toBeTruthy();
            }
        });
        studentExamDialogPage.setGradeInput('5');
        expect(studentExamDialogPage.getGradeInput()).toMatch('5');
        studentExamDialogPage.studentSelectLastOption();
        studentExamDialogPage.examSelectLastOption();
        studentExamDialogPage.evaluatedBySelectLastOption();
        studentExamDialogPage.save();
        expect(studentExamDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class StudentExamComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-student-exam div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class StudentExamDialogPage {
    modalTitle = element(by.css('h4#myStudentExamLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    attendedInput = element(by.css('input#field_attended'));
    gradeInput = element(by.css('input#field_grade'));
    studentSelect = element(by.css('select#field_student'));
    examSelect = element(by.css('select#field_exam'));
    evaluatedBySelect = element(by.css('select#field_evaluatedBy'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    getAttendedInput = function() {
        return this.attendedInput;
    };
    setGradeInput = function(grade) {
        this.gradeInput.sendKeys(grade);
    };

    getGradeInput = function() {
        return this.gradeInput.getAttribute('value');
    };

    studentSelectLastOption = function() {
        this.studentSelect.all(by.tagName('option')).last().click();
    };

    studentSelectOption = function(option) {
        this.studentSelect.sendKeys(option);
    };

    getStudentSelect = function() {
        return this.studentSelect;
    };

    getStudentSelectedOption = function() {
        return this.studentSelect.element(by.css('option:checked')).getText();
    };

    examSelectLastOption = function() {
        this.examSelect.all(by.tagName('option')).last().click();
    };

    examSelectOption = function(option) {
        this.examSelect.sendKeys(option);
    };

    getExamSelect = function() {
        return this.examSelect;
    };

    getExamSelectedOption = function() {
        return this.examSelect.element(by.css('option:checked')).getText();
    };

    evaluatedBySelectLastOption = function() {
        this.evaluatedBySelect.all(by.tagName('option')).last().click();
    };

    evaluatedBySelectOption = function(option) {
        this.evaluatedBySelect.sendKeys(option);
    };

    getEvaluatedBySelect = function() {
        return this.evaluatedBySelect;
    };

    getEvaluatedBySelectedOption = function() {
        return this.evaluatedBySelect.element(by.css('option:checked')).getText();
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
