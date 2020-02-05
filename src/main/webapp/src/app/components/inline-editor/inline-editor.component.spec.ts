import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { InlineEditorComponent } from './inline-editor.component';

describe('InlineEditorComponent', () => {
  let component: InlineEditorComponent;
  let fixture: ComponentFixture<InlineEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InlineEditorComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(InlineEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
