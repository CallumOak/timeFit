import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoutineCardComponent } from './routine-card.component';

describe('RoutineCardComponent', () => {
  let component: RoutineCardComponent;
  let fixture: ComponentFixture<RoutineCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoutineCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoutineCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
