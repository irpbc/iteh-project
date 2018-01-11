import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('SchoolYear e2e test', () => {

    let navBarPage: NavBarPage;
    let schoolYearDialogPage: SchoolYearDialogPage;
    let schoolYearComponentsPage: SchoolYearComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SchoolYears', () => {
        navBarPage.goToEntity('school-year');
        schoolYearComponentsPage = new SchoolYearComponentsPage();
        expect(schoolYearComponentsPage.getTitle()).toMatch(/itehProjectApp.schoolYear.home.title/);

    });

    it('should load create SchoolYear dialog', () => {
        schoolYearComponentsPage.clickOnCreateButton();
        schoolYearDialogPage = new SchoolYearDialogPage();
        expect(schoolYearDialogPage.getModalTitle()).toMatch(/itehProjectApp.schoolYear.home.createOrEditLabel/);
        schoolYearDialogPage.close();
    });

    it('should create and save SchoolYears', () => {
        schoolYearComponentsPage.clickOnCreateButton();
        schoolYearDialogPage.setNameInput('name');
        expect(schoolYearDialogPage.getNameInput()).toMatch('name');
        schoolYearDialogPage.setStartDateInput('2000-12-31');
        expect(schoolYearDialogPage.getStartDateInput()).toMatch('2000-12-31');
        schoolYearDialogPage.setEndDateInput('2000-12-31');
        expect(schoolYearDialogPage.getEndDateInput()).toMatch('2000-12-31');
        schoolYearDialogPage.save();
        expect(schoolYearDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SchoolYearComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-school-year div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SchoolYearDialogPage {
    modalTitle = element(by.css('h4#mySchoolYearLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    startDateInput = element(by.css('input#field_startDate'));
    endDateInput = element(by.css('input#field_endDate'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setStartDateInput = function(startDate) {
        this.startDateInput.sendKeys(startDate);
    };

    getStartDateInput = function() {
        return this.startDateInput.getAttribute('value');
    };

    setEndDateInput = function(endDate) {
        this.endDateInput.sendKeys(endDate);
    };

    getEndDateInput = function() {
        return this.endDateInput.getAttribute('value');
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
