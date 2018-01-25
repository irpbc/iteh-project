import { Component, Input } from '@angular/core';
import { ControlContainer, NgForm } from '@angular/forms';

@Component({
    selector: 'checkbox-input',
    viewProviders: [{ provide: ControlContainer, useExisting: NgForm }],
    template: `
        <div class="form-check">
            <label class="form-check-label" for="{{field}}">
                <input class="form-check-input" [disabled]="disableCreate && object.id === null" type="checkbox" id="{{field}}"
                       name="{{field}}" [(ngModel)]="object[field]">
                <span jhiTranslate="app.{{entityName}}.{{field}}"></span>
            </label>
        </div>`,
})
export class CheckboxInputComponent {

    @Input() public form: NgForm;
    @Input() public entityName: string;
    @Input() public field: string;
    @Input() public object: any;
    @Input() public disableCreate: boolean;
}
