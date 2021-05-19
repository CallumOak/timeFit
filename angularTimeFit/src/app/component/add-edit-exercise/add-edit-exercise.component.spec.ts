import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditExerciseComponent } from './add-edit-exercise.component';

describe('AddEditExerciseComponent', () => {
  let component: AddEditExerciseComponent;
  let fixture: ComponentFixture<AddEditExerciseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEditExerciseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditExerciseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
