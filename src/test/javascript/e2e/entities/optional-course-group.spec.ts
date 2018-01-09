import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('OptionalCourseGroup e2e test', () => {

    let navBarPage: NavBarPage;
    let optionalCourseGroupDialogPage: OptionalCourseGroupDialogPage;
    let optionalCourseGroupComponentsPage: OptionalCourseGroupComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load OptionalCourseGroups', () => {
        navBarPage.goToEntity('optional-course-group');
        optionalCourseGroupComponentsPage = new OptionalCourseGroupComponentsPage();
        expect(optionalCourseGroupComponentsPage.getTitle()).toMatch(/itehProjectApp.optionalCourseGroup.home.title/);

    });

    it('should load create OptionalCourseGroup dialog', () => {
        optionalCourseGroupComponentsPage.clickOnCreateButton();
        optionalCourseGroupDialogPage = new OptionalCourseGroupDialogPage();
        expect(optionalCourseGroupDialogPage.getModalTitle()).toMatch(/itehProjectApp.optionalCourseGroup.home.createOrEditLabel/);
        optionalCourseGroupDialogPage.close();
    });

   /* it('should create and save OptionalCourseGroups', () => {
        optionalCourseGroupComponentsPage.clickOnCreateButton();
        optionalCourseGroupDialogPage.setNameInput('name');
        expect(optionalCourseGroupDialogPage.getNameInput()).toMatch('name');
        optionalCourseGroupDialogPage.semesterSelectLastOption();
        optionalCourseGroupDialogPage.save();
        expect(optionalCourseGroupDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OptionalCourseGroupComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-optional-course-group div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class OptionalCourseGroupDialogPage {
    modalTitle = element(by.css('h4#myOptionalCourseGroupLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    semesterSelect = element(by.css('select#field_semester'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    }

    semesterSelectLastOption = function() {
        this.semesterSelect.all(by.tagName('option')).last().click();
    }

    semesterSelectOption = function(option) {
        this.semesterSelect.sendKeys(option);
    }

    getSemesterSelect = function() {
        return this.semesterSelect;
    }

    getSemesterSelectedOption = function() {
        return this.semesterSelect.element(by.css('option:checked')).getText();
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
