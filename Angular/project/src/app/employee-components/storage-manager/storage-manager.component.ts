import { Component, OnInit } from '@angular/core';
import {StorageService} from "../../service/company/storage.service";
import {Storage} from "../../model/storage";

@Component({
  selector: 'app-storage-manager',
  templateUrl: './storage-manager.component.html',
  styleUrls: ['./storage-manager.component.css']
})
export class StorageManagerComponent implements OnInit {

  public storages: Storage[] = [];
  public search:string = "";

  constructor(private storageService: StorageService) { }

  ngOnInit(): void {
    this.initStorage();
  }

  private initStorage(){
    this.storageService.getAllStorage().subscribe(
      resp=>{
        this.storages = resp;
      }
    )
  }

  addStorageOnClick() {
    let location = prompt("Write location");
    if (location) {
      this.storageService.addStorage(location).subscribe(
        resp => {
          console.log(resp);
          this.initStorage();
        }
      );
    }
  }

  clearSearch(){
    this.search = "";
  }

  filterStorage(): Storage[]{
    return this.storages.filter(s=>s.location.includes(this.search));
  }
}
