import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { BindPage } from './bind.page';

describe('BindPage', () => {
  let component: BindPage;
  let fixture: ComponentFixture<BindPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BindPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(BindPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
