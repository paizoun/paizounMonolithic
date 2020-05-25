import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PaizounTestModule } from '../../../test.module';
import { EstadoPartidoComponent } from 'app/entities/estado-partido/estado-partido.component';
import { EstadoPartidoService } from 'app/entities/estado-partido/estado-partido.service';
import { EstadoPartido } from 'app/shared/model/estado-partido.model';

describe('Component Tests', () => {
  describe('EstadoPartido Management Component', () => {
    let comp: EstadoPartidoComponent;
    let fixture: ComponentFixture<EstadoPartidoComponent>;
    let service: EstadoPartidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [EstadoPartidoComponent]
      })
        .overrideTemplate(EstadoPartidoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoPartidoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoPartidoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EstadoPartido(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.estadoPartidos && comp.estadoPartidos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
