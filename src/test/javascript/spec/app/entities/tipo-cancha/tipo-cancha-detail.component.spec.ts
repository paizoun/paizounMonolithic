import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { TipoCanchaDetailComponent } from 'app/entities/tipo-cancha/tipo-cancha-detail.component';
import { TipoCancha } from 'app/shared/model/tipo-cancha.model';

describe('Component Tests', () => {
  describe('TipoCancha Management Detail Component', () => {
    let comp: TipoCanchaDetailComponent;
    let fixture: ComponentFixture<TipoCanchaDetailComponent>;
    const route = ({ data: of({ tipoCancha: new TipoCancha(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [TipoCanchaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoCanchaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoCanchaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoCancha on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoCancha).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
