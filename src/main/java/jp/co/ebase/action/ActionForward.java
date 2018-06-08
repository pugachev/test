package jp.co.ebase.action;

/**
 * 非推奨
 * Spring用Struts疑似クラス.
 */
public class ActionForward
{
	/**
	 * 遷移先
	 */
	private String forward;

	/**
	 * 遷移先を設定する
	 *
	 * @param nextPage 遷移先
	 */
	public ActionForward(String forward)
	{
		// TODO 自動生成されたメソッド・スタブ
		this.forward = forward;
	}

	/**
	 * 遷移先を取得する
	 *
	 * @return 遷移先
	 */
	public String getForward()
	{
		return this.forward;
	}
}