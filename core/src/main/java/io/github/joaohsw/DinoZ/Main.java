package io.github.joaohsw.DinoZ;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends ApplicationAdapter {
    private Stage palco;
    private Skin aparencia;
    private boolean jogo_iniciado;
    private SpriteBatch lote;
    private Texture textura;

    @Override
    public void create() {
        palco = new Stage(new FitViewport(1280, 720));
        Gdx.input.setInputProcessor(palco);

        aparencia = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Table tabela = new Table();
        tabela.setFillParent(true);
        palco.addActor(tabela);

        Label titulo = new Label("DinoZ", aparencia);
        titulo.setFontScale(6); 

        TextButton botao_jogar = new TextButton("Jogar", aparencia);
        botao_jogar.getLabel().setFontScale(4f); 

        TextButton botao_sair = new TextButton("Sair", aparencia);
        botao_sair.getLabel().setFontScale(4f); 

        botao_jogar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogo_iniciado = true;
                lote = new SpriteBatch();
                textura = new Texture("background.png");
            }
        });

        botao_sair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        tabela.add(titulo).padBottom(40).row();
        tabela.add(botao_jogar).fillX().uniformX().padBottom(20).row();
        tabela.add(botao_sair).fillX().uniformX();

        Gdx.input.setInputProcessor(palco);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        if (jogo_iniciado) {
            lote.begin();
            lote.draw(textura, 0, 0, 1280, 720);
            lote.end();
        } else {
            palco.act(Gdx.graphics.getDeltaTime());
            palco.draw();
        }
    }

    @Override
    public void resize(int largura, int altura) {
        palco.getViewport().update(largura, altura);
    }

    @Override
    public void dispose() {
        palco.dispose();
        aparencia.dispose();
        if (lote != null) lote.dispose();
        if (textura != null) textura.dispose();
    }
}