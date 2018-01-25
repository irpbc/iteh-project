import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Course e2e test', () => {

    let navBarPage: NavBarPage;
    let courseDialogPage: CourseDialogPage;
    let courseComponentsPage: CourseComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Courses', () => {
        navBarPage.goToEntity('course');
        courseComponentsPage = new CourseComponentsPage();
        expect(courseComponentsPage.getTitle()).toMatch(/app.course.home.title/);

    });

    it('should load create Course dialog', () => {
        courseComponentsPage.clickOnCreateButton();
        courseDialogPage = new CourseDialogPage();
        expect(courseDialogPage.getModalTitle()).toMatch(/app.course.home.createOrEditLabel/);
        courseDialogPage.close();
    });

   /* it('should create and save Courses', () => {
        courseComponentsPage.clickOnCreateButton();
        courseDialogPage.setNameInput('name');
        expect(courseDialogPage.getNameInput()).toMatch('name');
        courseDialogPage.setEspbPointsInput('5');
        expect(courseDialogPage.getEspbPointsInput()).toMatch('5');
        courseDialogPage.setYearOfStudiesInput('5');
        expect(courseDialogPage.getYearOfStudiesInput()).toMatch('5');
        courseDialogPage.getOptionalInput().isSelected().then((selected) => {
            if (selected) {
                courseDialogPage.getOptionalInput().click();
                expect(courseDialogPage.getOptionalInput().isSelected()).toBeFalsy();
            } else {
                courseDialogPage.getOptionalInput().click();
                expect(courseDialogPage.getOptionalInput().isSelected()).toBeTruthy();
            }
        });
        courseDialogPage.semesterSelectLastOption();
        courseDialogPage.optionalGroupSelectLastOption();
        // courseDialogPage.lecturersSelectLastOption();
        courseDialogPage.save();
        expect(courseDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CourseComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-course div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CourseDialogPage {
    modalTitle = element(by.css('h4#myCourseLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    espbPointsInput = element(by.css('input#field_espbPoints'));
    yearOfStudiesInput = element(by.css('input#field_yearOfStudies'));
    optionalInput = element(by.css('input#field_optional'));
    semesterSelect = element(by.css('select#field_semester'));
    optionalGroupSelect = element(by.css('select#field_optionalGroup'));
    lecturersSelect = element(by.css('select#field_lecturers'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setEspbPointsInput = function(espbPoints) {
        this.espbPointsInput.sendKeys(espbPoints);
    };

    getEspbPointsInput = function() {
        return this.espbPointsInput.getAttribute('value');
    };

    setYearOfStudiesInput = function(yearOfStudies) {
        this.yearOfStudiesInput.sendKeys(yearOfStudies);
    };

    getYearOfStudiesInput = function() {
        return this.yearOfStudiesInput.getAttribute('value');
    };

    getOptionalInput = function() {
        return this.optionalInput;
    };
    semesterSelectLastOption = function() {
        this.semesterSelect.all(by.tagName('option')).last().click();
    };

    semesterSelectOption = function(option) {
        this.semesterSelect.sendKeys(option);
    };

    getSemesterSelect = function() {
        return this.semesterSelect;
    };

    getSemesterSelectedOption = function() {
        return this.semesterSelect.element(by.css('option:checked')).getText();
    };

    optionalGroupSelectLastOption = function() {
        this.optionalGroupSelect.all(by.tagName('option')).last().click();
    };

    optionalGroupSelectOption = function(option) {
        this.optionalGroupSelect.sendKeys(option);
    };

    getOptionalGroupSelect = function() {
        return this.optionalGroupSelect;
    };

    getOptionalGroupSelectedOption = function() {
        return this.optionalGroupSelect.element(by.css('option:checked')).getText();
    };

    lecturersSelectLastOption = function() {
        this.lecturersSelect.all(by.tagName('option')).last().click();
    };

    lecturersSelectOption = function(option) {
        this.lecturersSelect.sendKeys(option);
    };

    getLecturersSelect = function() {
        return this.lecturersSelect;
    };

    getLecturersSelectedOption = function() {
        return this.lecturersSelect.element(by.css('option:checked')).getText();
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
