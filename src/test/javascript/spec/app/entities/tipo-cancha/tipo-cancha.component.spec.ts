import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PaizounTestModule } from '../../../test.module';
import { TipoCanchaComponent } from 'app/entities/tipo-cancha/tipo-cancha.component';
import { TipoCanchaService } from 'app/entities/tipo-cancha/tipo-cancha.service';
import { TipoCancha } from 'app/shared/model/tipo-cancha.model';

describe('Component Tests', () => {
  describe('TipoCancha Management Component', () => {
    let comp: TipoCanchaComponent;
    let fixture: ComponentFixture<TipoCanchaComponent>;
    let service: TipoCanchaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [TipoCanchaComponent]
      })
        .overrideTemplate(TipoCanchaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoCanchaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoCanchaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoCancha(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoCanchas && comp.tipoCanchas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
