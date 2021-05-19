import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditRoutineComponent } from './add-edit-routine.component';

describe('AddEditRoutineComponent', () => {
  let component: AddEditRoutineComponent;
  let fixture: ComponentFixture<AddEditRoutineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEditRoutineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditRoutineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
