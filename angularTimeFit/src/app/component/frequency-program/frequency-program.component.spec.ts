import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FrequencyProgramComponent } from './frequency-program.component';

describe('FrequencyProgramComponent', () => {
  let component: FrequencyProgramComponent;
  let fixture: ComponentFixture<FrequencyProgramComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FrequencyProgramComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FrequencyProgramComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
