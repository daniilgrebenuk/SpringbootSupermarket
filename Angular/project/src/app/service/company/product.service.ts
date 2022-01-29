import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {Category, Product} from "../../model/product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private productUrl = environment.productApiUrl;
  private categoryUrl = environment.categoryApiUrl;

  constructor(private http: HttpClient) {
  }

  public getAllProduct(): Observable<any> {
    return this.http.get(`${this.productUrl}/all`);
  }

  public getAllProductByCategoryId(id: number): Observable<any> {
    return this.http.get(`${this.productUrl}/all/category/${id}`);
  }

  public addProduct(product: Product): Observable<any> {
    return this.http.post(`${this.productUrl}/add`, product);
  }

  public updateProduct(product: Product): Observable<any> {
    return this.http.put(`${this.productUrl}/update`, product);
  }

  public deleteProduct(id: number) : Observable<any>{
    return this.http.delete(`${this.productUrl}/delete/${id}`)
  }

  public getAllCategory(): Observable<any> {
    return this.http.get(`${this.categoryUrl}/all`);
  }

  public addCategory(category: any): Observable<any> {
    return this.http.post(`${this.categoryUrl}/add`, category);
  }

  public updateCategory(category: Category): Observable<any> {
    return this.http.put(`${this.categoryUrl}/update`, category);
  }

  public deleteCategory(id: number): Observable<any>{
    return this.http.delete(`${this.categoryUrl}/delete/${id}`)
  }
}
