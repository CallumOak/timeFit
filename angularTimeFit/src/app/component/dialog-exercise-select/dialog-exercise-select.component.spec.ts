import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogExerciseSelectComponent } from './dialog-exercise-select.component';

describe('DialogExerciseSelectComponent', () => {
  let component: DialogExerciseSelectComponent;
  let fixture: ComponentFixture<DialogExerciseSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogExerciseSelectComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogExerciseSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
