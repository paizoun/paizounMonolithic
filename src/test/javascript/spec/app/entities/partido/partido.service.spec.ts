import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PartidoService } from 'app/entities/partido/partido.service';
import { IPartido, Partido } from 'app/shared/model/partido.model';

describe('Service Tests', () => {
  describe('Partido Service', () => {
    let injector: TestBed;
    let service: PartidoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPartido;
    let expectedResult: IPartido | IPartido[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PartidoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Partido(0, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaHora: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Partido', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaHora: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaHora: currentDate
          },
          returnedFromService
        );

        service.create(new Partido()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Partido', () => {
        const returnedFromService = Object.assign(
          {
            fechaHora: currentDate.format(DATE_TIME_FORMAT),
            finalizado: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaHora: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Partido', () => {
        const returnedFromService = Object.assign(
          {
            fechaHora: currentDate.format(DATE_TIME_FORMAT),
            finalizado: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaHora: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Partido', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
