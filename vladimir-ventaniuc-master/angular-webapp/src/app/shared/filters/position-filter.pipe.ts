import { Pipe, PipeTransform } from '@angular/core';
import {Player} from '../models/player';
@Pipe({
    name: 'positionfilter',
    pure: false
})
export class PositionFilterPipe implements PipeTransform {
    transform(items: Player[], filter: Object): any {
        if (!items || !filter) {
            return items;
        }
        // filter items array, items which match and return true will be
        // kept, false will be filtered out
        return items.filter(item => item.position.indexOf(filter["position"]) !== -1);
    }
}

