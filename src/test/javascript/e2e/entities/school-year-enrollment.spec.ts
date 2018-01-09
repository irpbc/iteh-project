import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('SchoolYearEnrollment e2e test', () => {

    let navBarPage: NavBarPage;
    let schoolYearEnrollmentDialogPage: SchoolYearEnrollmentDialogPage;
    let schoolYearEnrollmentComponentsPage: SchoolYearEnrollmentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SchoolYearEnrollments', () => {
        navBarPage.goToEntity('school-year-enrollment');
        schoolYearEnrollmentComponentsPage = new SchoolYearEnrollmentComponentsPage();
        expect(schoolYearEnrollmentComponentsPage.getTitle()).toMatch(/itehProjectApp.schoolYearEnrollment.home.title/);

    });

    it('should load create SchoolYearEnrollment dialog', () => {
        schoolYearEnrollmentComponentsPage.clickOnCreateButton();
        schoolYearEnrollmentDialogPage = new SchoolYearEnrollmentDialogPage();
        expect(schoolYearEnrollmentDialogPage.getModalTitle()).toMatch(/itehProjectApp.schoolYearEnrollment.home.createOrEditLabel/);
        schoolYearEnrollmentDialogPage.close();
    });

   /* it('should create and save SchoolYearEnrollments', () => {
        schoolYearEnrollmentComponentsPage.clickOnCreateButton();
        schoolYearEnrollmentDialogPage.setAverageGradeInput('5');
        expect(schoolYearEnrollmentDialogPage.getAverageGradeInput()).toMatch('5');
        schoolYearEnrollmentDialogPage.setEspbPointsDeclaredInput('5');
        expect(schoolYearEnrollmentDialogPage.getEspbPointsDeclaredInput()).toMatch('5');
        schoolYearEnrollmentDialogPage.setEspbPointsAttainedInput('5');
        expect(schoolYearEnrollmentDialogPage.getEspbPointsAttainedInput()).toMatch('5');
        schoolYearEnrollmentDialogPage.studentSelectLastOption();
        schoolYearEnrollmentDialogPage.yearSelectLastOption();
        schoolYearEnrollmentDialogPage.save();
        expect(schoolYearEnrollmentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SchoolYearEnrollmentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-school-year-enrollment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SchoolYearEnrollmentDialogPage {
    modalTitle = element(by.css('h4#mySchoolYearEnrollmentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    averageGradeInput = element(by.css('input#field_averageGrade'));
    espbPointsDeclaredInput = element(by.css('input#field_espbPointsDeclared'));
    espbPointsAttainedInput = element(by.css('input#field_espbPointsAttained'));
    studentSelect = element(by.css('select#field_student'));
    yearSelect = element(by.css('select#field_year'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setAverageGradeInput = function(averageGrade) {
        this.averageGradeInput.sendKeys(averageGrade);
    }

    getAverageGradeInput = function() {
        return this.averageGradeInput.getAttribute('value');
    }

    setEspbPointsDeclaredInput = function(espbPointsDeclared) {
        this.espbPointsDeclaredInput.sendKeys(espbPointsDeclared);
    }

    getEspbPointsDeclaredInput = function() {
        return this.espbPointsDeclaredInput.getAttribute('value');
    }

    setEspbPointsAttainedInput = function(espbPointsAttained) {
        this.espbPointsAttainedInput.sendKeys(espbPointsAttained);
    }

    getEspbPointsAttainedInput = function() {
        return this.espbPointsAttainedInput.getAttribute('value');
    }

    studentSelectLastOption = function() {
        this.studentSelect.all(by.tagName('option')).last().click();
    }

    studentSelectOption = function(option) {
        this.studentSelect.sendKeys(option);
    }

    getStudentSelect = function() {
        return this.studentSelect;
    }

    getStudentSelectedOption = function() {
        return this.studentSelect.element(by.css('option:checked')).getText();
    }

    yearSelectLastOption = function() {
        this.yearSelect.all(by.tagName('option')).last().click();
    }

    yearSelectOption = function(option) {
        this.yearSelect.sendKeys(option);
    }

    getYearSelect = function() {
        return this.yearSelect;
    }

    getYearSelectedOption = function() {
        return this.yearSelect.element(by.css('option:checked')).getText();
    }

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
