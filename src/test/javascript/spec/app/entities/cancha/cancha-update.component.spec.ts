import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { CanchaUpdateComponent } from 'app/entities/cancha/cancha-update.component';
import { CanchaService } from 'app/entities/cancha/cancha.service';
import { Cancha } from 'app/shared/model/cancha.model';

describe('Component Tests', () => {
  describe('Cancha Management Update Component', () => {
    let comp: CanchaUpdateComponent;
    let fixture: ComponentFixture<CanchaUpdateComponent>;
    let service: CanchaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [CanchaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CanchaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CanchaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CanchaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cancha(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cancha();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
