import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoutineVisualizerComponent } from './routine-visualizer.component';

describe('RoutineVisualizerComponent', () => {
  let component: RoutineVisualizerComponent;
  let fixture: ComponentFixture<RoutineVisualizerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoutineVisualizerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoutineVisualizerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
