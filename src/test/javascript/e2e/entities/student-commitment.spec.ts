import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('StudentCommitment e2e test', () => {

    let navBarPage: NavBarPage;
    let studentCommitmentDialogPage: StudentCommitmentDialogPage;
    let studentCommitmentComponentsPage: StudentCommitmentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load StudentCommitments', () => {
        navBarPage.goToEntity('student-commitment');
        studentCommitmentComponentsPage = new StudentCommitmentComponentsPage();
        expect(studentCommitmentComponentsPage.getTitle()).toMatch(/itehProjectApp.studentCommitment.home.title/);

    });

    it('should load create StudentCommitment dialog', () => {
        studentCommitmentComponentsPage.clickOnCreateButton();
        studentCommitmentDialogPage = new StudentCommitmentDialogPage();
        expect(studentCommitmentDialogPage.getModalTitle()).toMatch(/itehProjectApp.studentCommitment.home.createOrEditLabel/);
        studentCommitmentDialogPage.close();
    });

   /* it('should create and save StudentCommitments', () => {
        studentCommitmentComponentsPage.clickOnCreateButton();
        studentCommitmentDialogPage.setPointsInput('5');
        expect(studentCommitmentDialogPage.getPointsInput()).toMatch('5');
        studentCommitmentDialogPage.enrollmentSelectLastOption();
        studentCommitmentDialogPage.commitmentSelectLastOption();
        studentCommitmentDialogPage.evaluatedBySelectLastOption();
        studentCommitmentDialogPage.save();
        expect(studentCommitmentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class StudentCommitmentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-student-commitment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class StudentCommitmentDialogPage {
    modalTitle = element(by.css('h4#myStudentCommitmentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    pointsInput = element(by.css('input#field_points'));
    enrollmentSelect = element(by.css('select#field_enrollment'));
    commitmentSelect = element(by.css('select#field_commitment'));
    evaluatedBySelect = element(by.css('select#field_evaluatedBy'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setPointsInput = function(points) {
        this.pointsInput.sendKeys(points);
    };

    getPointsInput = function() {
        return this.pointsInput.getAttribute('value');
    };

    enrollmentSelectLastOption = function() {
        this.enrollmentSelect.all(by.tagName('option')).last().click();
    };

    enrollmentSelectOption = function(option) {
        this.enrollmentSelect.sendKeys(option);
    };

    getEnrollmentSelect = function() {
        return this.enrollmentSelect;
    };

    getEnrollmentSelectedOption = function() {
        return this.enrollmentSelect.element(by.css('option:checked')).getText();
    };

    commitmentSelectLastOption = function() {
        this.commitmentSelect.all(by.tagName('option')).last().click();
    };

    commitmentSelectOption = function(option) {
        this.commitmentSelect.sendKeys(option);
    };

    getCommitmentSelect = function() {
        return this.commitmentSelect;
    };

    getCommitmentSelectedOption = function() {
        return this.commitmentSelect.element(by.css('option:checked')).getText();
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
