import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoCancha } from 'app/shared/model/tipo-cancha.model';

type EntityResponseType = HttpResponse<ITipoCancha>;
type EntityArrayResponseType = HttpResponse<ITipoCancha[]>;

@Injectable({ providedIn: 'root' })
export class TipoCanchaService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-canchas';

  constructor(protected http: HttpClient) {}

  create(tipoCancha: ITipoCancha): Observable<EntityResponseType> {
    return this.http.post<ITipoCancha>(this.resourceUrl, tipoCancha, { observe: 'response' });
  }

  update(tipoCancha: ITipoCancha): Observable<EntityResponseType> {
    return this.http.put<ITipoCancha>(this.resourceUrl, tipoCancha, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoCancha>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoCancha[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
