import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Semester e2e test', () => {

    let navBarPage: NavBarPage;
    let semesterDialogPage: SemesterDialogPage;
    let semesterComponentsPage: SemesterComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Semesters', () => {
        navBarPage.goToEntity('semester');
        semesterComponentsPage = new SemesterComponentsPage();
        expect(semesterComponentsPage.getTitle()).toMatch(/app.semester.home.title/);

    });

    it('should load create Semester dialog', () => {
        semesterComponentsPage.clickOnCreateButton();
        semesterDialogPage = new SemesterDialogPage();
        expect(semesterDialogPage.getModalTitle()).toMatch(/app.semester.home.createOrEditLabel/);
        semesterDialogPage.close();
    });

   /* it('should create and save Semesters', () => {
        semesterComponentsPage.clickOnCreateButton();
        semesterDialogPage.setNameInput('name');
        expect(semesterDialogPage.getNameInput()).toMatch('name');
        semesterDialogPage.yearSelectLastOption();
        semesterDialogPage.save();
        expect(semesterDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SemesterComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-semester div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SemesterDialogPage {
    modalTitle = element(by.css('h4#mySemesterLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    yearSelect = element(by.css('select#field_year'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    yearSelectLastOption = function() {
        this.yearSelect.all(by.tagName('option')).last().click();
    };

    yearSelectOption = function(option) {
        this.yearSelect.sendKeys(option);
    };

    getYearSelect = function() {
        return this.yearSelect;
    };

    getYearSelectedOption = function() {
        return this.yearSelect.element(by.css('option:checked')).getText();
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
