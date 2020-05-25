import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InvitacionPartidoService } from 'app/entities/invitacion-partido/invitacion-partido.service';
import { IInvitacionPartido, InvitacionPartido } from 'app/shared/model/invitacion-partido.model';

describe('Service Tests', () => {
  describe('InvitacionPartido Service', () => {
    let injector: TestBed;
    let service: InvitacionPartidoService;
    let httpMock: HttpTestingController;
    let elemDefault: IInvitacionPartido;
    let expectedResult: IInvitacionPartido | IInvitacionPartido[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InvitacionPartidoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new InvitacionPartido(0, currentDate, currentDate, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaHoraCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaHoraPartido: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a InvitacionPartido', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaHoraCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaHoraPartido: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaHoraCreacion: currentDate,
            fechaHoraPartido: currentDate
          },
          returnedFromService
        );

        service.create(new InvitacionPartido()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a InvitacionPartido', () => {
        const returnedFromService = Object.assign(
          {
            fechaHoraCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaHoraPartido: currentDate.format(DATE_TIME_FORMAT),
            equipoAConfirmado: true,
            equipoBConfirmado: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaHoraCreacion: currentDate,
            fechaHoraPartido: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of InvitacionPartido', () => {
        const returnedFromService = Object.assign(
          {
            fechaHoraCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaHoraPartido: currentDate.format(DATE_TIME_FORMAT),
            equipoAConfirmado: true,
            equipoBConfirmado: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaHoraCreacion: currentDate,
            fechaHoraPartido: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a InvitacionPartido', () => {
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
