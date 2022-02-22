import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../service/company/product.service";
import {Category, Product} from "../../model/product";
import {NgForm} from "@angular/forms";
import {createLogErrorHandler} from "@angular/compiler-cli/ngcc/src/execution/tasks/completion";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-product-manager',
  templateUrl: './product-manager.component.html',
  styleUrls: ['./product-manager.component.css']
})
export class ProductManagerComponent implements OnInit {

  public products: Product[] = [];
  public categories: Category[] = [];
  public searchOption: string = "";
  public currentCategory: Category | null = null;
  public currentProduct: Product | null = null;

  private categoryIsLoad = false;
  private productIsLoad = false;

  constructor(private productService: ProductService) {
    this.initAllCategoryAndProduct()
  }

  ngOnInit(): void {

  }

  clearSearch() {
    this.searchOption = "";
  }

  getProductsByCategoryId(category: Category) {
    this.currentCategory = category;
    this.productService.getAllProductByCategoryId(category.id).subscribe(
      resp => this.products = resp,
      error => this.products = []
    );
  }

  getAllProductClick() {
    this.currentCategory = null;
    this.getAllProduct();
  }

  getAllProduct() {
    this.productService.getAllProduct().subscribe(
      resp => {
        this.products = resp;
        this.productIsLoad = true;
        this.hideSpinnerAndShowAddButtons();
      }
    );
  }

  initAllCategoryAndProduct() {
    this.getAllCategory()
    this.initAllProduct()
  }

  initAllProduct() {
    this.currentProduct = null;
    if (this.currentCategory) {
      this.productService.getAllProductByCategoryId(this.currentCategory.id).subscribe(
        resp => {
          this.products = resp;
        },
        error => {
          this.products = []
        }
      )
    } else {
      this.getAllProduct();
    }
  }

  private getAllCategory() {
    this.productService.getAllCategory().subscribe(
      resp => {
        this.categories = resp;
        this.categoryIsLoad = true;
        this.hideSpinnerAndShowAddButtons();
      }
    );
  }

  onAddCategoryClick() {
    let name = prompt("Write category name");
    if (name) {
      this.productService.addCategory({name: name}).subscribe(
        resp => {
          console.log(resp);
          this.getAllCategory();
        }
      );
    }
  }

  onAddProductClick() {
    let modal = document.getElementById('modal-add-product');
    let modalBody = document.getElementById('modal-add-body');

    modal!.style.opacity = '1';
    modal!.style.zIndex = '2';
    modalBody!.style.top = "3em";
    modalBody!.style.bottom = "3em";
  }


  closeModal() {
    this.currentProduct = null;
    let modal = document.getElementById('modal-add-product');
    let modalBody = document.getElementById('modal-add-body');

    modal!.style.opacity = '0';
    modal!.style.zIndex = '-1';
    modalBody!.style.top = "4em";
    modalBody!.style.bottom = "2em";
  }

  onSaveProduct(form: NgForm) {
    let category: Category = this.categories.filter(c => c.name == form.value.category)[0];
    let product: Product = form.value;
    product.category = category;

    if (this.currentProduct) {
      product.id = this.currentProduct.id;
      this.productService.updateProduct(product).subscribe(
        resp => this.initAllProduct()
      );
    } else {
      this.productService.addProduct(product).subscribe(
        resp => this.initAllProduct()
      );
    }
    console.log(this.categories)

    this.closeModal();

  }

  onDeleteProduct() {
    if (!this.currentProduct) {
      return;
    }

    this.productService.deleteProduct(this.currentProduct.id).subscribe(
      resp => this.initAllProduct()
    )
    this.closeModal();
  }

  onProductCLick(product: Product) {
    this.currentProduct = product;
    this.onAddProductClick();
  }

  getProductsWithSearch(): Product[] {
    return this.products.filter(p => p.name.includes(this.searchOption));
  }

  private hideSpinnerAndShowAddButtons() {
    if (this.categoryIsLoad && this.productIsLoad) {
      let spinner = document.getElementById("spinner");
      let products = document.getElementById("products");
      let categories = document.getElementById("categories");
      let search = document.getElementById("search");
      spinner?.remove();

      products!.style.opacity = "1";
      categories!.style.opacity = "1";
      search!.style.marginTop = "0";
    }
  }

}
