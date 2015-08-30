export function unique(arr) {
	return arr.filter((val, idx, arr) => {
           if (idx > 0 && val === arr[idx-1]) {
               return false;
           } else {
               return true;
           }
        });
}