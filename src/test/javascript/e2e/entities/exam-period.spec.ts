import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('ExamPeriod e2e test', () => {

    let navBarPage: NavBarPage;
    let examPeriodDialogPage: ExamPeriodDialogPage;
    let examPeriodComponentsPage: ExamPeriodComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ExamPeriods', () => {
        navBarPage.goToEntity('exam-period');
        examPeriodComponentsPage = new ExamPeriodComponentsPage();
        expect(examPeriodComponentsPage.getTitle()).toMatch(/itehProjectApp.examPeriod.home.title/);

    });

    it('should load create ExamPeriod dialog', () => {
        examPeriodComponentsPage.clickOnCreateButton();
        examPeriodDialogPage = new ExamPeriodDialogPage();
        expect(examPeriodDialogPage.getModalTitle()).toMatch(/itehProjectApp.examPeriod.home.createOrEditLabel/);
        examPeriodDialogPage.close();
    });

   /* it('should create and save ExamPeriods', () => {
        examPeriodComponentsPage.clickOnCreateButton();
        examPeriodDialogPage.setNameInput('name');
        expect(examPeriodDialogPage.getNameInput()).toMatch('name');
        examPeriodDialogPage.setStartDateInput('2000-12-31');
        expect(examPeriodDialogPage.getStartDateInput()).toMatch('2000-12-31');
        examPeriodDialogPage.setEndDateInput('2000-12-31');
        expect(examPeriodDialogPage.getEndDateInput()).toMatch('2000-12-31');
        examPeriodDialogPage.yearSelectLastOption();
        examPeriodDialogPage.save();
        expect(examPeriodDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ExamPeriodComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-exam-period div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ExamPeriodDialogPage {
    modalTitle = element(by.css('h4#myExamPeriodLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    startDateInput = element(by.css('input#field_startDate'));
    endDateInput = element(by.css('input#field_endDate'));
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
