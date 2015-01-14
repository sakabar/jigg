package enju.pipeline

import java.util.Properties
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class KNPAnnotatorTest extends FunSuite {
  test("getTokens") {
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-0.93093
                   |* -1D <文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ -1D <文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><判定詞><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><用言代表表記:太郎/たろう><時制-無時制><照応詞候補:太郎><格解析結果:太郎/たろう:判0:ガ/U/-/-/-/-;ニ/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;ヘ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;修飾/U/-/-/-/-;ノ/U/-/-/-/-;ガ２/U/-/-/-/-;ニトル/U/-/-/-/-><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><文末><表現文末><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞>
                   |EOS
""".stripMargin.split("\n").toSeq

    val expected_tokens = <tokens><token surf="太郎" reading="たろう" base="太郎" pos="名詞" pos_id="6" pos1="人名" pos1_id="5" inflectionType="*" inflectionType_id="0" inflectionForm="*" inflectionForm_id="0" features="人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう &lt;人名:日本:名:45:0.00106&gt;&lt;疑似代表表記&gt;&lt;代表表記:太郎/たろう&gt;&lt;正規化代表表記:太郎/たろう&gt;&lt;文頭&gt;&lt;文末&gt;&lt;表現文末&gt;&lt;漢字&gt;&lt;かな漢字&gt;&lt;名詞相当語&gt;&lt;自立&gt;&lt;内容語&gt;&lt;タグ単位始&gt;&lt;文節始&gt;&lt;固有キー&gt;&lt;文節主辞&gt;" id="s0_0"/></tokens>

    val knp = new KNPAnnotator("knp", new Properties)

    knp.getTokens(input, "s0") should be(expected_tokens)
  }

  test("getBasicPhrases") {
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-0.93093
                   |* -1D <文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ -1D <文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><判定詞><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><用言代表表記:太郎/たろう><時制-無時制><照応詞候補:太郎><格解析結果:太郎/たろう:判0:ガ/U/-/-/-/-;ニ/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;ヘ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;修飾/U/-/-/-/-;ノ/U/-/-/-/-;ガ２/U/-/-/-/-;ニトル/U/-/-/-/-><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><文末><表現文末><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞>
                   |EOS
""".stripMargin.split("\n").toSeq

    val feature = "<文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><判定詞><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><用言代表表記:太郎/たろう><時制-無時制><照応詞候補:太郎><格解析結果:太郎/たろう:判0:ガ/U/-/-/-/-;ニ/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;ヘ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;修飾/U/-/-/-/-;ノ/U/-/-/-/-;ガ２/U/-/-/-/-;ニトル/U/-/-/-/-><EID:0>"
    val expected = <basic_phrases><basic_phrase id="s0_0" tokens="s0_0" features={feature} /></basic_phrases>

    val knp = new KNPAnnotator("knp", new Properties)

    knp.getBasicPhrases(input, "s0") should be(expected)
  }

  test("isChunk") {
    val input = "* -1D <文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>"

    val knp = new KNPAnnotator("knp", new Properties)
    knp.isChunk(input) should be(true)
  }

  test("isBasicPhrase") {
    val input = "+ -1D <文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><判定詞><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><用言代表表記:太郎/たろう><時制-無時制><照応詞候補:太郎><格解析結果:太郎/たろう:判0:ガ/U/-/-/-/-;ニ/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;ヘ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;修飾/U/-/-/-/-;ノ/U/-/-/-/-;ガ２/U/-/-/-/-;ニトル/U/-/-/-/-><EID:0>"

    val knp = new KNPAnnotator("knp", new Properties)
    knp.isBasicPhrase(input) should be(true)
  }

  test("isDocInfo") {
    val input = "# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-0.93093"

    val knp = new KNPAnnotator("knp", new Properties)
    knp.isDocInfo(input) should be(true)
  }

  test("isToken") {
    val input = "太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 \"人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう\" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><文末><表現文末><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞>"

    val knp = new KNPAnnotator("knp", new Properties)
    knp.isToken(input) should be(true)
  }

  test("isEOS") {
    val input = "EOS"

    val knp = new KNPAnnotator("knp", new Properties)
    knp.isEOS(input) should be(true)
  }

  test("getBasicPhrase 2") {
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-7.16850
                   |* 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><NE:PERSON:太郎><照応詞候補:太郎><解析格:ガ><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞><係:ガ格><NE:PERSON:S>
                   |が が が 助詞 9 格助詞 1 * 0 * 0 NIL <かな漢字><ひらがな><付属>
                   |* -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>
                   |+ -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><主題格:一人称優位><格関係0:ガ:太郎><格解析結果:走る/はしる:動13:ガ/C/太郎/0/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:1><述語項構造:走る/はしる:動13:ガ/C/太郎/0>
                   |走った はしった 走る 動詞 2 * 0 子音動詞ラ行 10 タ形 10 "代表表記:走る/はしる" <代表表記:走る/はしる><正規化代表表記:走る/はしる><表現文末><かな漢字><活用語><自立><内容語><タグ単位始><文節始><文節主辞>
                   |。 。 。 特殊 1 句点 1 * 0 * 0 NIL <文末><英記号><記号><付属>
                   |EOS
""".stripMargin.split("\n").toSeq

    val feature1 = "<文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><NE:PERSON:太郎><照応詞候補:太郎><解析格:ガ><EID:0>"
    val feature2 = "<文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><主題格:一人称優位><格関係0:ガ:太郎><格解析結果:走る/はしる:動13:ガ/C/太郎/0/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:1><述語項構造:走る/はしる:動13:ガ/C/太郎/0>"
    val expected = <basic_phrases><basic_phrase id="s0_0" tokens="s0_0 s0_1" features={feature1} /><basic_phrase id="s0_1" tokens="s0_2 s0_3" features={feature2} /></basic_phrases>

    val knp = new KNPAnnotator("knp", new Properties)

    knp.getBasicPhrases(input, "s0") should be(expected)
  }

  test("getChunks") {
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-7.16850
                   |* 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><NE:PERSON:太郎><照応詞候補:太郎><解析格:ガ><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞><係:ガ格><NE:PERSON:S>
                   |が が が 助詞 9 格助詞 1 * 0 * 0 NIL <かな漢字><ひらがな><付属>
                   |* -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>
                   |+ -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><主題格:一人称優位><格関係0:ガ:太郎><格解析結果:走る/はしる:動13:ガ/C/太郎/0/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:1><述語項構造:走る/はしる:動13:ガ/C/太郎/0>
                   |走った はしった 走る 動詞 2 * 0 子音動詞ラ行 10 タ形 10 "代表表記:走る/はしる" <代表表記:走る/はしる><正規化代表表記:走る/はしる><表現文末><かな漢字><活用語><自立><内容語><タグ単位始><文節始><文節主辞>
                   |。 。 。 特殊 1 句点 1 * 0 * 0 NIL <文末><英記号><記号><付属>
                   |EOS
""".stripMargin.split("\n").toSeq

    val feature1 = "<文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>"
    val feature2 = "<文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>"

    val expected = <chunks><chunk id="s0_0" tokens="s0_0 s0_1" features={feature1} /><chunk id="s0_1" tokens="s0_2 s0_3" features={feature2}/></chunks>

    val knp = new KNPAnnotator("knp", new Properties)
    knp.getChunks(input, "s0") should be(expected)
  }

  test("getBasicPhraseDependencies") {
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-7.16850
                   |* 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><NE:PERSON:太郎><照応詞候補:太郎><解析格:ガ><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞><係:ガ格><NE:PERSON:S>
                   |が が が 助詞 9 格助詞 1 * 0 * 0 NIL <かな漢字><ひらがな><付属>
                   |* -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>
                   |+ -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><主題格:一人称優位><格関係0:ガ:太郎><格解析結果:走る/はしる:動13:ガ/C/太郎/0/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:1><述語項構造:走る/はしる:動13:ガ/C/太郎/0>
                   |走った はしった 走る 動詞 2 * 0 子音動詞ラ行 10 タ形 10 "代表表記:走る/はしる" <代表表記:走る/はしる><正規化代表表記:走る/はしる><表現文末><かな漢字><活用語><自立><内容語><タグ単位始><文節始><文節主辞>
                   |。 。 。 特殊 1 句点 1 * 0 * 0 NIL <文末><英記号><記号><付属>
                   |EOS
""".stripMargin.split("\n").toSeq

    val expected = <basic_phrase_dependencies root="s0_1"><basic_phrase_dependency id="s0_0" head="s0_1" dependent="s0_0" label="D"/></basic_phrase_dependencies>


    val knp = new KNPAnnotator("knp", new Properties)
    knp.getBasicPhraseDependencies(input, "s0") should be(expected)
  }

  test("getDependencies"){
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-7.16850
                   |* 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><NE:PERSON:太郎><照応詞候補:太郎><解析格:ガ><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞><係:ガ格><NE:PERSON:S>
                   |が が が 助詞 9 格助詞 1 * 0 * 0 NIL <かな漢字><ひらがな><付属>
                   |* -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>
                   |+ -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><主題格:一人称優位><格関係0:ガ:太郎><格解析結果:走る/はしる:動13:ガ/C/太郎/0/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:1><述語項構造:走る/はしる:動13:ガ/C/太郎/0>
                   |走った はしった 走る 動詞 2 * 0 子音動詞ラ行 10 タ形 10 "代表表記:走る/はしる" <代表表記:走る/はしる><正規化代表表記:走る/はしる><表現文末><かな漢字><活用語><自立><内容語><タグ単位始><文節始><文節主辞>
                   |。 。 。 特殊 1 句点 1 * 0 * 0 NIL <文末><英記号><記号><付属>
                   |EOS
""".stripMargin.split("\n").toSeq

    val expected = <dependencies root="s0_1"><dependency id="s0_0" head="s0_1" dependent="s0_0" label="D"/></dependencies>

    val knp = new KNPAnnotator("knp", new Properties)
    knp.getDependencies(input, "s0") should be(expected)
  }

  test("getCaseRelations"){
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-7.16850
                   |* 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><NE:PERSON:太郎><照応詞候補:太郎><解析格:ガ><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞><係:ガ格><NE:PERSON:S>
                   |が が が 助詞 9 格助詞 1 * 0 * 0 NIL <かな漢字><ひらがな><付属>
                   |* -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>
                   |+ -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><主題格:一人称優位><格関係0:ガ:太郎><格解析結果:走る/はしる:動13:ガ/C/太郎/0/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:1><述語項構造:走る/はしる:動13:ガ/C/太郎/0>
                   |走った はしった 走る 動詞 2 * 0 子音動詞ラ行 10 タ形 10 "代表表記:走る/はしる" <代表表記:走る/はしる><正規化代表表記:走る/はしる><表現文末><かな漢字><活用語><自立><内容語><タグ単位始><文節始><文節主辞>
                   |。 。 。 特殊 1 句点 1 * 0 * 0 NIL <文末><英記号><記号><付属>
                   |EOS
""".stripMargin.split("\n").toSeq

    val expected = <case_relations><case_relation id="s0_0" head="s0_1" depend="s0_0" label="ガ" flag="C" /><case_relation id="s0_1" head="s0_1" depend="unk" label="ヲ" flag="U" /><case_relation id="s0_2" head="s0_1" depend="unk" label="ニ" flag="U" /><case_relation id="s0_3" head="s0_1" depend="unk" label="ト" flag="U" /><case_relation id="s0_4" head="s0_1" depend="unk" label="デ" flag="U" /><case_relation id="s0_5" head="s0_1" depend="unk" label="カラ" flag="U" /><case_relation id="s0_6" head="s0_1" depend="unk" label="ヨリ" flag="U" /><case_relation id="s0_7" head="s0_1" depend="unk" label="マデ" flag="U" /><case_relation id="s0_8" head="s0_1" depend="unk" label="時間" flag="U" /><case_relation id="s0_9" head="s0_1" depend="unk" label="外の関係" flag="U" /><case_relation id="s0_10" head="s0_1" depend="unk" label="ノ" flag="U" /><case_relation id="s0_11" head="s0_1" depend="unk" label="修飾" flag="U" /><case_relation id="s0_12" head="s0_1" depend="unk" label="トスル" flag="U" /><case_relation id="s0_13" head="s0_1" depend="unk" label="ニオク" flag="U" /><case_relation id="s0_14" head="s0_1" depend="unk" label="ニカンスル" flag="U" /><case_relation id="s0_15" head="s0_1" depend="unk" label="ニヨル" flag="U" /><case_relation id="s0_16" head="s0_1" depend="unk" label="ヲフクメル" flag="U" /><case_relation id="s0_17" head="s0_1" depend="unk" label="ヲハジメル" flag="U" /><case_relation id="s0_18" head="s0_1" depend="unk" label="ヲノゾク" flag="U" /><case_relation id="s0_19" head="s0_1" depend="unk" label="ヲツウジル" flag="U" /></case_relations>

    val knp = new KNPAnnotator("knp", new Properties)
    val tokens = knp.getTokens(input, "s0")
    val bps = knp.getBasicPhrases(input, "s0")
    knp.getCaseRelations(input, tokens, bps, "s0") should be (expected)
  }
}
