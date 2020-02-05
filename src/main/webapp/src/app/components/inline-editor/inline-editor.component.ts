import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';

@Component({
  selector: 'inline-editor',
  templateUrl: './inline-editor.component.html',
  styleUrls: ['./inline-editor.component.scss'],
})
export class InlineEditorComponent implements OnChanges{
  @Input() value: string;
  newValue: string;
  @Output() valueChange = new EventEmitter<string>();
  editing: boolean;

  ngOnChanges(): void {
    this.newValue = this.value;
  }

  startEditing(): void {
    this.editing = true;
  }

  accept(): void {
    this.value = this.newValue;
    this.valueChange.emit(this.newValue);
    this.editing = false;
  }

  cancel(): void {
    this.newValue = this.value;
    this.editing = false;
  }
}
