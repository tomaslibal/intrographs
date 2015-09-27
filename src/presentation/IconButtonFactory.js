import iconButtonImages from './model/iconButtonImages';
import IconButton from './model/IconButton';

export default class IconButtonFactory {
	static create(id, icon, width=64, height=64, disabled=false) {
		const imgUrl = iconButtonImages[icon];

		let ib = new IconButton();

		ib.id = id;
		ib.icon = imgUrl;
		ib.width = width;
		ib.height = height;
		ib.disabled = disabled;

		return ib;
	}
}