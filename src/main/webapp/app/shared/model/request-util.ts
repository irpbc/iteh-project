import { URLSearchParams, BaseRequestOptions } from '@angular/http';

export const createRequestOption = (req?: any): BaseRequestOptions => {
    const options: BaseRequestOptions = new BaseRequestOptions();
    if (req) {
        const params: URLSearchParams = new URLSearchParams();
        params.set('page', req.page);
        params.set('size', req.size);
        if (req.sort) {
            params.paramsMap.set('sort', req.sort);
        }
        params.set('query', req.query);

        if (req.filter) {
            for (const key in req.filter) {
                if (req.filter.hasOwnProperty(key)) {
                    params.set(key, req.filter[key]);
                }
            }
        }

        options.params = params;
    }
    return options;
};
