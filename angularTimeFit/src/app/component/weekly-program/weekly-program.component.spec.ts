import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WeeklyProgramComponent } from './weekly-program.component';

describe('WeeklyProgramComponent', () => {
  let component: WeeklyProgramComponent;
  let fixture: ComponentFixture<WeeklyProgramComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WeeklyProgramComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WeeklyProgramComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
