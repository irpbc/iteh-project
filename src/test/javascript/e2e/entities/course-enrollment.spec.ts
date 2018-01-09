import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('CourseEnrollment e2e test', () => {

    let navBarPage: NavBarPage;
    let courseEnrollmentDialogPage: CourseEnrollmentDialogPage;
    let courseEnrollmentComponentsPage: CourseEnrollmentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CourseEnrollments', () => {
        navBarPage.goToEntity('course-enrollment');
        courseEnrollmentComponentsPage = new CourseEnrollmentComponentsPage();
        expect(courseEnrollmentComponentsPage.getTitle()).toMatch(/itehProjectApp.courseEnrollment.home.title/);

    });

    it('should load create CourseEnrollment dialog', () => {
        courseEnrollmentComponentsPage.clickOnCreateButton();
        courseEnrollmentDialogPage = new CourseEnrollmentDialogPage();
        expect(courseEnrollmentDialogPage.getModalTitle()).toMatch(/itehProjectApp.courseEnrollment.home.createOrEditLabel/);
        courseEnrollmentDialogPage.close();
    });

   /* it('should create and save CourseEnrollments', () => {
        courseEnrollmentComponentsPage.clickOnCreateButton();
        courseEnrollmentDialogPage.setTotalPointsInput('5');
        expect(courseEnrollmentDialogPage.getTotalPointsInput()).toMatch('5');
        courseEnrollmentDialogPage.setGradeInput('5');
        expect(courseEnrollmentDialogPage.getGradeInput()).toMatch('5');
        courseEnrollmentDialogPage.getCompletedInput().isSelected().then((selected) => {
            if (selected) {
                courseEnrollmentDialogPage.getCompletedInput().click();
                expect(courseEnrollmentDialogPage.getCompletedInput().isSelected()).toBeFalsy();
            } else {
                courseEnrollmentDialogPage.getCompletedInput().click();
                expect(courseEnrollmentDialogPage.getCompletedInput().isSelected()).toBeTruthy();
            }
        });
        courseEnrollmentDialogPage.yearEnrollmentSelectLastOption();
        courseEnrollmentDialogPage.courseSelectLastOption();
        courseEnrollmentDialogPage.save();
        expect(courseEnrollmentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CourseEnrollmentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-course-enrollment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CourseEnrollmentDialogPage {
    modalTitle = element(by.css('h4#myCourseEnrollmentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    totalPointsInput = element(by.css('input#field_totalPoints'));
    gradeInput = element(by.css('input#field_grade'));
    completedInput = element(by.css('input#field_completed'));
    yearEnrollmentSelect = element(by.css('select#field_yearEnrollment'));
    courseSelect = element(by.css('select#field_course'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTotalPointsInput = function(totalPoints) {
        this.totalPointsInput.sendKeys(totalPoints);
    }

    getTotalPointsInput = function() {
        return this.totalPointsInput.getAttribute('value');
    }

    setGradeInput = function(grade) {
        this.gradeInput.sendKeys(grade);
    }

    getGradeInput = function() {
        return this.gradeInput.getAttribute('value');
    }

    getCompletedInput = function() {
        return this.completedInput;
    }
    yearEnrollmentSelectLastOption = function() {
        this.yearEnrollmentSelect.all(by.tagName('option')).last().click();
    }

    yearEnrollmentSelectOption = function(option) {
        this.yearEnrollmentSelect.sendKeys(option);
    }

    getYearEnrollmentSelect = function() {
        return this.yearEnrollmentSelect;
    }

    getYearEnrollmentSelectedOption = function() {
        return this.yearEnrollmentSelect.element(by.css('option:checked')).getText();
    }

    courseSelectLastOption = function() {
        this.courseSelect.all(by.tagName('option')).last().click();
    }

    courseSelectOption = function(option) {
        this.courseSelect.sendKeys(option);
    }

    getCourseSelect = function() {
        return this.courseSelect;
    }

    getCourseSelectedOption = function() {
        return this.courseSelect.element(by.css('option:checked')).getText();
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
